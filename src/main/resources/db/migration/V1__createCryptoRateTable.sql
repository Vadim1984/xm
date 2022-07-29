CREATE TABLE IF NOT EXISTS `crypto_rate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `crypto_currency` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `time_stamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8