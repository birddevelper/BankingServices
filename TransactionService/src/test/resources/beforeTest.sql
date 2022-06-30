TRUNCATE TABLE `transaction`;

INSERT INTO `transaction` (`id`,`account`,`credit`,`debit`,`transaction_time`,`description`) VALUES
        (1,12345678,100,0,'2022-06-27 14:33:11','Initial credit'),
        (2,12345678,0,40,'2022-06-28 14:15:11','Withdraw internet bank'),
        (3,87654321,200,0,'2022-06-29 14:33:11','Initial credit');