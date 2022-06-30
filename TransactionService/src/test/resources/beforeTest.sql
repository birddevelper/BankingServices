TRUNCATE TABLE `transaction`;

INSERT INTO `transaction` (`account`,`credit`,`debit`,`transaction_time`,`description`) VALUES
        (12345678,100,0,'2022-06-27 14:33:11','Initial credit'),
        (12345678,0,40,'2022-06-28 14:15:11','Withdraw internet bank'),
        (87654321,200,0,'2022-06-29 14:33:11','Initial credit');