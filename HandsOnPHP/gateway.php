<?php

require("./api.php");
$api = new API();

$domain = $_POST['domain'];
$function = $_POST['function'];
$data = $_POST['data'];
$result = $api->handleRequest($domain, $function, $data);

echo($result);

?>