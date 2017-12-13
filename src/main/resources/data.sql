insert into user (id, nickname)
values ('1', '제프 윙거');

insert into user (id, nickname)
values ('2', '브리타 패리');

insert into user (id, nickname)
values ('3', '애니 애디슨');

insert into user (id, nickname)
values ('4', '트로이 반스');

insert into user (id, nickname)
values ('5', '아벳 나디르');
	
insert into issue (id, title, description, priority, status, assignee, reporter, resolver, create_date, update_date, resolved_date)
values (issue_seq.nextval, 'test1', '<script>alert(1);</script>', 'A', 'OPEN', '1', '1', '', sysdate, null, null);

insert into issue (id, title, description, priority, status, assignee, reporter, resolver, create_date, update_date, resolved_date)
values (issue_seq.nextval, 'test2', ';alert(1);', 'A', 'IN_PROGRESS', '1', '5', '', sysdate, null, null);

insert into issue (id, title, description, priority, status, assignee, reporter, resolver, create_date, update_date, resolved_date)
values (issue_seq.nextval, 'test3', 'test33333', 'B', 'RESOLVED', '2', '4', '2', sysdate, sysdate, sysdate);

insert into issue (id, title, description, priority, status, assignee, reporter, resolver, create_date, update_date, resolved_date)
values (issue_seq.nextval, 'test4', 'test44444', 'B', 'CLOSED', '3', '4', '3', sysdate, sysdate, sysdate);

insert into comment(id, issue_id, comment, create_user, update_user, create_date, update_date)
values ('1', '2', 'test test', '5', null, sysdate, null);

insert into comment(id, issue_id, comment, create_user, update_user, create_date, update_date)
values ('2', '2', 'test test22222', '1', null, sysdate, null);

insert into code (id, code_group, code)
values ('1', 'PRIORITY', 'A');

insert into code (id, code_group, code)
values ('2', 'PRIORITY', 'B');

insert into code (id, code_group, code)
values ('3', 'PRIORITY', 'C');

insert into label (code_id, label, lang)
values ('1', '매우중요', 'ko');

insert into label (code_id, label, lang)
values ('1', 'Critical', 'en');

insert into label (code_id, label, lang)
values ('2', '중요', 'ko');

insert into label (code_id, label, lang)
values ('2', 'Major', 'en');

insert into label (code_id, label, lang)
values ('3', '보통', 'ko');

insert into label (code_id, label, lang)
values ('3', 'Minor', 'en');

insert into code (id, code_group, code)
values ('4', 'STATUS', 'OPEN');

insert into code (id, code_group, code)
values ('5', 'STATUS', 'IN_PROGRESS');

insert into code (id, code_group, code)
values ('6', 'STATUS', 'RESOLVED');

insert into code (id, code_group, code)
values ('7', 'STATUS', 'CLOSED');

insert into code (id, code_group, code)
values ('8', 'STATUS', 'CANCEL');

insert into label (code_id, label, lang)
values ('4', 'Open', 'ko');

insert into label (code_id, label, lang)
values ('4', 'Open', 'en');

insert into label (code_id, label, lang)
values ('5', 'In Progress', 'ko');

insert into label (code_id, label, lang)
values ('5', 'In Progress', 'en');

insert into label (code_id, label, lang)
values ('6', 'Resolved', 'ko');

insert into label (code_id, label, lang)
values ('6', 'Resolved', 'en');

insert into label (code_id, label, lang)
values ('7', 'Closed', 'ko');

insert into label (code_id, label, lang)
values ('7', 'Closed', 'en');

insert into label (code_id, label, lang)
values ('8', 'Cancel', 'ko');

insert into label (code_id, label, lang)
values ('8', 'Cancel', 'en');