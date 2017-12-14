$(function () {
	    "use strict";
    
    /**
     * 리스트 영역 Controller 
     */
    var listController = (function() {
    	
		/* CSRF 처리 */
    	var handleCsrf = function() {
    		$.ajaxSetup({
    			beforeSend : function(xhr, settings) {
    			  if (settings.type == 'POST' || settings.type == 'PATCH'
    			      || settings.type == 'DELETE') {
    			    if (!(/^http:.*/.test(settings.url) || /^https:.*/.test(settings.url))) {
    			      xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
    			    }
    			  }
    			}
    		});
    	};
    	
    	/* 상단 현재 접속자 정보 세팅 */
    	var setUserInfo = function(nickname, profileImageSrc) {
    		$("#user").html(nickname);
	        $("#profileImage").attr('src', profileImageSrc);
    	};
    	
    	var hideLoginAndShowBoard = function() {
    		$(".unauthenticated").hide();
	        $(".authenticated").show();
    	};
    	
    	/* 그리드 초기화 */
    	var loadGrid = function() {
    		$("#grid").jqGrid({
    	        colModel: [
    	            { name: "id", label: "Issue No." },
    	            { name: "title", label: "Title" },
    	            { name: "priorityLabel", label: "Priority" },
    	            { name: "statusLabel", label: "Status" },
    	            { name: "assigneeLabel", label: "Assignee" },
    	            { name: "reporterLabel", label: "Reporter" },
    	            { name: "resolverLabel", label: "Resolver" },
    	        ],
    	        datatype: "clientSide",
    	        onSelectRow: function(rowid) {
    	        	var id = $(this).jqGrid('getCell', rowid, 'id');
    	        	openBoard(id);
    	        }
    	    });
    	};
    	
    	/* 검색 */
    	var search = function() {
    		$.ajax({
    	        type: 'GET',
    	        contentType: "application/json",
    	        url: "/api/issues",
    	        data: $('.search form').serialize(),
    	        dataType: 'json',
    	        cache: false
			}).done(function(data) {
	    		$('#grid')
	    			.jqGrid('clearGridData')
	    			.jqGrid('setGridParam', {data: data})
	    			.trigger('reloadGrid');
			});
    	};
    	
    	/* 로그인 여부에 따라 화면 처리 */
    	var handleLoginStutus = function() {
    		$.get("/api/me")
    		.done(function(data) { // 로그인 성공
    			setUserInfo(data.userAuthentication.details.response.nickname,
    					data.userAuthentication.details.response.profile_image);
    			hideLoginAndShowBoard();
    			loadGrid();
    		});
    	};
    	
    	var openBoard = function(id) {
    		$('.list').hide();
    		$('.board').show();
    		if (id) {
    			boardController.read(id);
    		}
    	};
    	
    	var attachRegistButtonEvent = function() {
    		$('#regist').click(function() {
    			openBoard();
    			event.preventDefault();
    		});
    	};
    	
    	var attachSearchButtonEvent = function() {
    		$('#search').click(function(event) {
    			search();
    			event.preventDefault();
    		});
    	};
    	
    	var pub = {
			init : function() {
				handleCsrf();
				handleLoginStutus();
				attachRegistButtonEvent();
				attachSearchButtonEvent();
				search();
			},
			search : function() {
				search();
			}
    	};
    	
    	return pub;
    }());
    
    /**
     * 이슈 본문 영역 Controller
     */
    var boardController = (function() {
    	
    	var initButtonAuthorization = function() {
    		// 상태에 따른 버튼 처리
			// OPEN - Assign, Start Progress, Save, Resolve, Close, Cancel
			// IN_PROGRESS - Assign, Stop Progress, Save, Resolve, Close, Cancel
			// RESOLVED - Assign, Save, Close, Cancel
			// CLOSE - Assign, Save, Reopen
    		$('#createBtn').addClass('TEMP').hide();
    		$('#saveBtn').addClass('OPEN').addClass('IN_PROGRESS').addClass('RESOLVED').addClass('CLOSED').addClass('CANCEL').hide();
    		$('#assignBtn').addClass('OPEN').addClass('IN_PROGRESS').addClass('RESOLVED').addClass('CLOSED').addClass('CANCEL').hide();
    		$('#startProgressBtn').addClass('OPEN').hide();
	    	$('#stopProgressBtn').addClass('IN_PROGRESS').hide();
	    	$('#resolveBtn').addClass('OPEN').addClass('IN_PROGRESS').hide();
	    	$('#closeBtn').addClass('OPEN').addClass('IN_PROGRESS').addClass('RESOLVED').hide();
	    	$('#reopenBtn').addClass('RESOLVED').addClass('CLOSED').addClass('CANCEL').hide();
	    	$('#cancelBtn').addClass('OPEN').addClass('IN_PROGRESS').addClass('RESOLVED').hide();
    	};
    	
    	var handleButtonVisible = function(status) {
    		$('.board .authorization').each(function() {
    			if ($(this).hasClass(status)) {
    				$(this).show();
    			} else {
    				$(this).hide();
    			}
    		});
    	};
    	
    	/* 등록 화면을 OPEN 상태로 만듦. */
    	var clearBoard = function() {
    		setBoardValue({}); 
    		handleButtonVisible('TEMP');
    		$('.board [name="priority"]').getOptions('/api/codegroups/PRIORITY/codes');
    		$('.board [name="assignee"]').getOptions('/api/users', {value : 'id', label : 'nickname'});
    		$('#popAssignee').getOptions('/api/users', {value : 'id', label : 'nickname'});
    	};
    	
    	var clearCommentList = function() {
    		$('.board .comments').html('');
    	};
    	
    	var goList = function() {
    		$('.list').show();
    		$('.board').hide();
    		clearBoard();
    		clearCommentList();
    	};
    	
    	/* 등록화면 값 세팅 */
    	var setBoardValue = function(board) {
    		$('.board [name]').each(function() {
    			$(this).val(board[$(this).attr('name')]);
    		});
    	};
    	
    	/* Comment List를 구성 */
    	var drawCommentList = function(comments) {
    		if (!comments) {
    			return;
    		}
    		
    		for(var i = 0, n = comments.length; i < n; i++) {
	    		$('.board .comments')
	    			.append('<li><p>' 
	    					+ comments[i].comment + '</p>'
	    					+ '<span>' + comments[i].createUserLabel + ', </span>'
	    					+ '<span>' + comments[i].createDate +  '</span></li>');
    		}
    	};
    	
    	var read = function(id) {
    		$('.board [name=id]').val(id);
    		return $.get("/api/issues/" + id)
    		.done(function(data) {
    			clearCommentList();
    			setBoardValue(data);
    			handleButtonVisible(data.status);
    			drawCommentList(data.comments);
    		});
    	};
    	
    	/* 이슈 생성 */
    	var create = function() {
    		return $.ajax({
    	        type: 'POST',
    	        contentType: 'application/json',
    	        url: '/api/issues',
    	        data: JSON.stringify($('#boardForm').serializeObject()),
    	        dataType: 'json',
    	        success: function(data) {
    	        	$('.board [name="id"]').val(data.id);
    	        	$('.board [name="statusLabel"]').val('Open');
    	        	alert('created.');
    	        	handleButtonVisible('OPEN');
    	        	listController.search();
    	        },
    	        error : function(e) {
    	        	alert(e.responseText);
    	        }
			});
    	};
    	
    	/* 이슈 수정 */
    	var save = function(id) {
    		return $.ajax({
    	        type: 'PATCH',
    	        contentType: 'application/json',
    	        url: '/api/issues/' + id,
    	        data: JSON.stringify($('#boardForm').serializeObject()),
    	        dataType: 'json',
    	        success: function(data) {
    	        	alert('saved.');
    	        	listController.search();
    	        },
    	        error : function(e) {
    	        	alert(e.responseText);
    	        }
			});
    	};
    	
    	/* 이슈 Status 변환 처리 */
    	var process = function(id, status, assignee, comment) {
    		var pramObj = $('#boardForm').serializeObject();
    		pramObj['assignee'] = assignee||pramObj.assignee;
    		pramObj['comment'] = comment||'';
    		pramObj['status'] = status||'';
    		
    		return $.ajax({
    	        type: 'PATCH',
    	        contentType: 'application/json',
    	        url: '/api/issues/' + id,
    	        data: JSON.stringify(pramObj),
    	        dataType: 'json',
    	        success: function(data) {
    	        	read(data.id)
    	        	.done(function(){
	    	        	listController.search();
	    	        	$('#dialog-confirm').dialog( "close" );
    	        	});
    	        	
    	        },
    	        error : function(e) {
    	        	alert(e.responseText);
    	        }
			});
    	};
    	
    	var getCommentList = function() {
    		var issueId = $('.board [name="id"]').val();
    		$.get('/api/issues/' + issueId + '/comments').then(function(data) {
    			clearCommentList();
    			drawCommentList(data);
    		}, function(e) {
    			alert(e.responseText);
    		});
    	};
    	
    	var sendComment = function() {
    		var issueId = $('.board [name="id"]').val();
    		var pramObj = {
    				issueId : issueId,
    				comment : $('.board [name="comment"]').val()
    		};
    		
    		return $.ajax({
    	        type: 'POST',
    	        contentType: 'application/json',
    	        url: '/api/issues/' + issueId + '/comments',
    	        data: JSON.stringify(pramObj),
    	        dataType: 'json',
    	        success: function(data) {
    	        	$('.board [name="comment"]').val('');
    	        	getCommentList();
    	        },
    	        error : function(e) {
    	        	alert(e.responseText);
    	        }
			});
    	};
    	
		var openProcessPopup = function(title, action) {
			$( "#dialog-confirm" ).attr('title', title);
			$( "#dialog-confirm" ).dialog({
    		      resizable: false,
    		      height: "auto",
    		      width: 400,
    		      modal: true,
    		      buttons: {
    		        Confirm: function() {
    		        	process($('.board [name="id"]').val(), action, $('#popAssignee').val(), $('#popComment').val())
    		        	.then(function(data) {
    		        		read($('.board [name="id"]').val());
							$( this ).dialog( "close" );
    		        	}, function(e) {
    		        		alert(e.responseText);
    		        	});
    		        },
    		        Cancel: function() {
    		          $( this ).dialog( "close" );
    		        }
    		      }
			});
		};
    	
    	var attachListButtonEvent = function() {
    		$('#listBtn').click(function() {
    			goList();
    		});
    	};
    	
    	var attachCreateButtonEvent = function() {
    		$('#createBtn').click(function() {
    			create();
    		});
    	};
    	
    	var attachSaveButtonEvent = function() {
    		$('#saveBtn').click(function() {
    			save($('.board [name="id"]').val());
    		});
    	};
    	
    	var attachStartButtonEvent = function() {
    		$('#startProgressBtn').click(function() {
    			process($('.board [name="id"]').val(), 'IN_PROGRESS');
    		});
    	};
    	
    	var attachStopButtonEvent = function() {
    		$('#stopProgressBtn').click(function() {
    			process($('.board [name="id"]').val(), 'OPEN');
    		});
    	};
    	
    	var attachAssignButtonEvent = function() {
    		$('#assignBtn').click(function() {
    			openProcessPopup('Assign', '');
    		});
    	};
    	
    	var attachResolveButtonEvent = function() {
    		$('#resolveBtn').click(function() {
    			openProcessPopup('Resolve', 'RESOLVED');
    		});
    	};
    	
    	var attachCloseButtonEvent = function() {
    		$('#closeBtn').click(function() {
    			openProcessPopup('Close', 'CLOSED');
    		});
    	};
    	
    	var attachReopenButtonEvent = function() {
    		$('#reopenBtn').click(function() {
    			openProcessPopup('Reopen', 'OPEN');
    		});
    	};
    	
    	var attachCancelButtonEvent = function() {
    		$('#cancelBtn').click(function() {
    			openProcessPopup('Cancel', 'CANCEL');
    		});
    	};
    	
    	var attachSendCommentButtonEvent = function() {
    		$('#sendComment').click(function(event) {
    			sendComment();
    			event.preventDefault();
    		});
    	};
    	
    	var pub = {
    		init : function() {
    			initButtonAuthorization();
    			clearBoard();
    			attachListButtonEvent();
    			attachCreateButtonEvent();
    			attachSaveButtonEvent();
    			attachStartButtonEvent();
    			attachStopButtonEvent();
    			attachAssignButtonEvent();
    			attachResolveButtonEvent();
    			attachCloseButtonEvent();
    			attachReopenButtonEvent();
    			attachCancelButtonEvent();
    			attachSendCommentButtonEvent();
    		},
    		read : function(id) {
    			read(id);
    		}
    	};
    	return pub;
    }());
	
	// List 화면 초기화
    listController.init();
	// 등록 화면 초기화
    boardController.init();
});

$.fn.getOptions = function (url, config) {
    "use strict";
    
    var defaultConfig = { 
		label : 'label',
		value : 'code'
	};
    
    if (!config) {
    	config = {};
    }
    
    for (var i in defaultConfig) {
    	if (!config[i]) {
    		config[i] = defaultConfig[i];
    	}
    }
    
    var elem = this;
    elem.html('');
    $.get(url)
	.done(function(data) {
		$.each(data, function(index, item) {
			var option = $('<option value="' + item[config.value] + '">' + item[config.label] + '</option>');
			if (config.defaultValue && item.code === config.defaultValue) {
				option.prop('selected', true);
			}
			elem.append(option);
		});
	});
};