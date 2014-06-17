CREATE TABLE IF NOT EXISTS user (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  time_registrated bigint NOT NULL,
  time_lastlogin bigint NULL,
  time_deleted bigint NULL,
  language varchar(40) NOT NULL,
  state int(11) NOT NULL,
  type int(11) NOT NULL,
  secret_magic varchar(255) NULL,
  activation_code varchar(255) NULL,
  time_token bigint NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM;