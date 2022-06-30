/*CREATE TABLE IF NOT EXISTS `customer` (
    `id` int(11) NOT NULL ,
    `name` varchar(255) DEFAULT NULL,
    `sure_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    );*/
TRUNCATE TABLE `customer`;
TRUNCATE TABLE `account`;

INSERT INTO `customer` (`id`, `name`, `sure_name`) VALUES
       (100,'Mostafa','Shaeri'),
       (200,'Alex', 'Tailor'),
       (300,'Mieke', 'Anderson');

INSERT INTO `account` (`account_number`,`customer_id`,`creation_date`) VALUES
        (12345678,100,'2022-06-27 14:33:11'),
        (87654321,200,'2022-06-28 11:31:01');