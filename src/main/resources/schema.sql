/* Setting up Prod DB */
DROP TABLE IF EXISTS `contact`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`       int           NOT NULL AUTO_INCREMENT,
    `email`    varchar(32)   NOT NULL,
    `password` varchar(16)   NOT NULL,
    `money`    double(12, 2) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `contact`
(
    `id`            int         NOT NULL AUTO_INCREMENT,
    `user_pmb_id`   varchar(32) NOT NULL,
    `fk_contact_id` int NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`),
    KEY `fk_contact_id_idx` (`fk_contact_id`),
    CONSTRAINT `fk_contact_id` FOREIGN KEY (`fk_contact_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



