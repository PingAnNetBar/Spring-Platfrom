create table user
(
    id  bigint primary key auto_increment,
    username varchar(100),
    encrypted_password varchar(100),
    avatar   varchar(1000),
    created_at datetime,
    updated_at datetime
)