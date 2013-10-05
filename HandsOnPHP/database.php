<?php
class Database {
	protected $con;
  
	public function connect() {
		$this->con = mysql_connect('ec2-184-73-92-152.compute-1.amazonaws.com','root','bitnami');
		if(!$this->con) {
			die("ERROR: Cannot connect to database!");
		}
	}
	
	public function selectWhere($table, $columns, $where_column, $where_operator, $where_value) {
		if ($this->con) {
			$result = $this->con.mysql_query("SELECT " . $columns . " FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value);
			if (!$result) {
				die ("Invalid query " . mysql_error());
			}
			return $result;
		}
	}
	
	public function selectLike($table, $columns, $where_column, $like_value) {
		if ($this->con) {
			$result = $this->con.mysql_query("SELECT " . $columns . " FROM " . $table . " WHERE " . $where_column . " LIKE '" . $like_value . "'");
			if (!$result) {
				die ("Invalid query " . mysql_error());
			}
			return $result;
		}
	}
	
	public function update($table, $columns, $where_column, $where_operator, $where_value, $values) {
		if ($this->con) {
			$result = $this-con.mysql_query("UPDATE " . $columns . " FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value . " VALUES " . $values);
			if (!$result) {
				die ("Invalid query " . mysql_error());
			}
			return $result;
		}
	}
	
	public function insert($table, $columns, $data) {
		if ($this->con) {
			$result = $this-con.mysql_query("INSERT INTO " . $table . " FIELDS " . $columns . " VALUES " . $values);
			if (!$result) {
				die ("Invalid query " . mysql_error());
			}
			return $result;
		}
	}
	
	public function drop($table, $where_column, $where_operator, $where_value) {
		if ($this->con) {
			$result = $this-con.mysql_query("DROP FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value);
			if (!$result) {
				die ("Invalid query " . mysql_error());
			}
			return $result;
		}
	}
}
?>