<?php

require("./search.php");
$search = new Search();
$result = $search->keyword("helps");
$row = $result->fetch_assoc();
echo($row['description']);

?>