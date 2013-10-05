<?php

/* SEARCH TEST
require("./search.php");
$search = new Search();
$result = $search->zipcode(43229);
$row = $result->fetch_all(MYSQLI_ASSOC);
$js = json_encode($row);
echo($js); */

require("./user.php");
$user = new User("ewhill", "password");
// $user->login();

?>