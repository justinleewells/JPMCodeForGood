<?php

class Emailer {

	public function send($data) {
		$url = "http://www.peerpeek.com/mail.php?" . http_build_query($data);
		$request = new HttpRequest($url, HTTP_METH_GET);
		$request->send();
	}
	
}

?>