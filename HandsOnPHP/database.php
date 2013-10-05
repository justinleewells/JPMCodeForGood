<?php

class Database {
	protected $con;
	
	function __construct() {
		echo('Database object created.');
	}
	
	public function connect() {
		$this->con = new mysqli('ec2-184-73-92-152.compute-1.amazonaws.com', 'root', 'bitnami', 'test');
		if ($this->con->connect_error) {
			die('Connect Error (' . $this->con->connect_errno . ') '
            . $this->con->connect_error);
		}
	}
	
	public function selectWhere($columns, $table, $where_column, $where_operator, $where_value) {
		if ($this->con) {
			$result = $this->con->query("SELECT " . $columns . " FROM " . $table . " WHERE " . $where_column . $where_operator . $where_value);
			if (!$result) {
				die ("Invalid query " . $this->con->error);
			}
			return $result;
		}
	}
	
	public function selectLike($columns, $table, $where_column, $like_value) {
		if ($this->con) {
			$result = $this->con->query("SELECT " . $columns . " FROM " . $table . " WHERE " . $where_column . " LIKE " . $like_value);
			if (!$result) {
				die ("Invalid query " . $this->con->error);
			}
			return $result;
		}
	}
	
	public function update($columns, $table, $where_column, $where_operator, $where_value, $values) {
		if ($this->con) {
			$result = $this->con->query("UPDATE " . $columns . " FROM " . $table . " WHERE " . $where_column . $where_operator . $where_value . " VALUES " . $values);
			if (!$result) {
				die ("Invalid query " . $this->con->error);
			}
			return $result;
		}
	}
	
	public function insert($table, $columns, $data) {
		if ($this->con) {
			echo "INSERT INTO " . $table . " " . $columns . " VALUES " . $values."<br>";
			$result = $this->con->query("INSERT INTO " . $table . " " . $columns . " VALUES " . $values);
			if (!$result) {
				die ("Invalid query " . $this->con->error);
			}
			return $result;
		}
	}
	
	public function delete($table, $where_column, $where_operator, $where_value) {
		if ($this->con) {
			$result = $this->con->query("DELETE FROM " . $table . " WHERE " . $where_column . " " . $where_operator . " " . $where_value);
			if (!$result) {
				die ("Invalid query " . $this->con->error);
			}
			return $result;
		}
	}
}

?>