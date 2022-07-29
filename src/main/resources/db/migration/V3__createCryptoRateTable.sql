CREATE TABLE `crypto_rate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` decimal(19,2) DEFAULT NULL,
  `time_stamp` datetime(6) DEFAULT NULL,
  `crypto_currency_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `crypto_currency_id_index` (`crypto_currency_id`),
  CONSTRAINT `crypto_currency_id_index` FOREIGN KEY (`crypto_currency_id`) REFERENCES `crypto_currency` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8