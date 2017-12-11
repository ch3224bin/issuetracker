create sequence issue_seq;

create table user (
	id varchar2(20),
	nickname varchar2(40)
);

create table issue (
	id varchar2(20),
	title varchar2(300),
	description varchar2(2000),
	priority varchar2(10),
	status varchar2(20),
	assignee varchar2(20),
	reporter varchar2(20),
	resolver varchar2(20),
	create_date date,
	update_date date,
	resolved_date date
);

create table code (
	id varchar2(20),
	code_group varchar2(30),
	code varchar2(30)
);

create table label (
	code_id varchar2(20),
	label varchar2(100),
	lang varchar2(3)
);