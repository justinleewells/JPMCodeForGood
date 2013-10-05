<?php
$con = mysql_connect('ec2-184-73-92-152.compute-1.amazonaws.com', 'root', 'bitnami') or die("FAILED");
mysql_select_db('test');
if ($con) {
	echo("Worked");
}

function dbSelectWhere($table, $columns, $where_column, $where_operator, $where_value) {
	if ($con) {
		$result = mysql_query("SELECT " . $columns . " FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value, $con);
		if (!$result) {
			die ("Invalid query " . mysql_error());
		}
		return $result;
	}
}

function dbSelectLike($table, $columns, $where_column, $like_value) {
	if ($con) {
		$result = mysql_query("SELECT " . $columns . " FROM " . $table . " WHERE " . $where_column . " LIKE '" . $like_value . "'", $con);
		if (!$result) {
			die ("Invalid query " . mysql_error());
		}
		return $result;
	}
}

function dbUpdate($table, $columns, $where_column, $where_operator, $where_value, $values) {
	if ($con) {
		$result = mysql_query("UPDATE " . $columns . " FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value . " VALUES " . $values, $con);
		if (!$result) {
			die ("Invalid query " . mysql_error());
		}
		return $result;
	}
}

function dbInsert($table, $columns, $data) {
	if ($con) {
		$result = mysql_query("INSERT INTO " . $table . " FIELDS " . $columns . " VALUES " . $values, $con);
		if (!$result) {
			die ("Invalid query " . mysql_error());
		}
		return $result;
	}
}

function dbDrop($table, $where_column, $where_operator, $where_value) {
	if ($con) {
		$result = mysql_query("DROP FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value, $con);
		if (!$result) {
			die ("Invalid query " . mysql_error());
		}
		return $result;
	}
}
?>