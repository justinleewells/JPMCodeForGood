<?php

require('user.php');
require('search.php');
require('event.php');

class API {

	public function handleRequest($domain, $function, $data) {
		
		$json = json_decode($data, true);
		
		switch($domain) {
		case 0:
			$result = $this->search($function, $json);
			break;
		case 1:
			$result = $this->user($function, $json);
			break;
		case 2:
			$result = $this->event($function, $json);
			break;
		}
		
		$encoded = json_encode($result);
		return $encoded;
	}
	
	private function search($function, $data) {
		$search = new Search();
		switch($function) {
		case 0:
			$result = $search->zipcode($function, $data['value']);
			break;
		case 1:
			$result = $search->keyword($function, $data['value']);
			break;
		case 2:
			$result = $search->impactArea($function, $data['value']);
			break;
		case 3:
			$result = $search->startDate($function, $data['value']);
			break;
		}
		$ret = $result->fetch_all(MYSQLI_ASSOC);
		return $ret;
	}
	
	private function user($function, $data) {
		$user = new User();
		switch($function) {
		case 0:
			$result = $user->login($data);
			break;
		case 1:
			$result = $user->logout($data);
			break;
		case 2:
			$result = $user->register($data);
			break;
		}
		$ret = $result->fetch_all(MYSQLI_ASSOC);
		return $result;
	}
	
	private function event($function, $data) {
		$event = new Event();
		switch($function) {
		case 0:
			$result = $event->getDetails($data['event_id']);
			break;
		case 1:
			$result = $event->subscribe($data['event_id'], $data['user_id']);
			break;
		case 2:
			$result = $event->unsubscribe($data['event_id'], $data['user_id']);
			break;
		}
		$ret = $result->fetch_all(MYSQLI_ASSOC);
		return $ret;
	}
}

?>