<?php

/* SEARCH TEST
require("./search.php");
$search = new Search();
$result = $search->zipcode(43229);
$row = $result->fetch_all(MYSQLI_ASSOC);
$js = json_encode($row);
echo($js); */

require("./event.php");
$event = new Event(); 

$result = $event->getDetails("a0CA0000008PQTz");
$row = $result->fetch_all(MYSQLI_ASSOC);
$js = json_encode($row);
echo($js)."<br>";

$result = $event->subscribe("a0CA0000008PQTz", "KeA5RyMVvk");

$result = $event->unsubscribe("a0CA0000008PQTz", "aasnfjjj10");

?>