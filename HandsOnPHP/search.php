<?php

require("./database.php");

/* SEARCH TYPES
 * ------------
 *	0 - KEYWORD
 *	1 - DATE
 *  2 - AREA OF INTEREST
 *  3 - ZIPCODE
 */
 
function search($search_type, $search_value) {
	$result = null;
	switch($search_type) {
	case 0:
		$result = $db->con->dbSelectLike("*", "events", "description", "'%" . $search_value . "%'");
		break;
	case 1:
		$result = $db->con->dbSelectWhere("*", "events", "start_date", "=", $search_value);
		break;
	case 2:
		$result = $db->con->dbSelectWhere("*", "events", "primary_impact_area", "=", $search_value);
		break;
	case 3:
		$result = $db->con->dbSelectWhere("*", "events", "zip_postal_code", "=", $search_value);
		break;
	default:
		$result = "Invalid Search Type";
	}
	return $result;
}

$type = $_GET['type'];
$value = $_GET['value'];
search($type, $value);

?>