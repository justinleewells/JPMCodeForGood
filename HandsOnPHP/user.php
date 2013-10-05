<?php

require_once("./database.php");

class User {
	protected $db;
	
	function __construct() {
		$this->db = new Database();
		$this->db->connect();
	}

	private function checkUsername($username) {
		$result = $this->db->selectWhere("*", "user", "username", "=", "'" . $username . "'");
		if ($result->num_rows>0) {
			return true;
		} else {
			return false;
		}
	}
	
	private function checkEmail($email) {
		$result = $this->db->selectWhere("*", "user", "email", "=", "'" . $email . "'");
		if ($result->num_rows>0) {
			return true;
		} else {
			return false;
		}
	}
	
	private function generateRandomString($length) {
		return substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $length);
  }
	
	public function login($data) {
		$result = $this->db->selectWhere("*", "user", "username", "=", "'" . $data['username'] . "'");
		$row = $result->fetch_assoc();
		if(strcmp($row['password'], $data['password']) == 0) {
			$this->db->update("logged_in", "user", "username", "=", "'" . $data['username'] . "'", 1);
			$result = array("logged_in" => 1, "msg" => "You successfully logged in.");
		} else {
			$result = array("logged_in" => 0, "msg" => "There was an error logging you in! Please check your password and try again.");
		}
		echo($data['password']);
		echo($row['password']);
		return $result;
	}
	
	public function logout($data) {
		$this->db->update("logged_in", "user", "username", "=", "'" . $data['username'] . "'", 0);
		$result = array("logged_in" => 0, "msg" => "You successfully logged out.");
		return $result;
	}
	
	public function register($data) {
		if ((!$this->checkUsername($data['username'])) && (!$this->checkEmail($data['email']))) {
			$id = $this->generateRandomString(15);
			$this->db->insert("user", "(id, first_name, last_name, username, email, address, password, phone, logged_in)", "('" . $id . "','" . $data['first_name'] . "','" . $data['last_name'] . "','" . $data['username'] . "','" . $data['email'] . "','" . $data['address'] . "','" . $data['password'] . "','" . $data['phone'] . "',0)");
			$result = array("logged_in" => 1, "msg" => "You logged in successfully!");
		}
		else {
			$result = array("logged_in" => 0, "msg" => "Your username or email is in use.");
		}
		return $result;
	}
	
}

?>