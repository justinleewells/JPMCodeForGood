<?php

/* SEARCH TEST
require("./search.php");
$search = new Search();
$result = $search->zipcode(43229);
$row = $result->fetch_all(MYSQLI_ASSOC);
$js = json_encode($row);
echo($js); */

require("./api.php");
$api = new API();
// $data = array("username" => "justin", "password" => "password", "email" => "email", "first_name" => "justin", "last_name" => "last_name", "phone" => "phone", "address" => "address");
$data = array("event_id" => "a0CA0000008PQvX", "user_id" => "aasnfjjj10");
$api->handleRequest(2, 1, $data);

?>