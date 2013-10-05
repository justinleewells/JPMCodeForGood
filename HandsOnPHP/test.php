<?php

/* SEARCH TEST
require("./search.php");
$search = new Search();
$result = $search->zipcode(43229);
$row = $result->fetch_all(MYSQLI_ASSOC);
$js = json_encode($row);
echo($js); */

require("./user.php");
$user = new User("justin", "password");
$data = array("username" => "justin", "password" => "password", "email" => "email", "first_name" => "justin", "last_name" => "last_name", "phone" => "phone", "address" => "address");
$user->register($data);
// $user->login();

?>