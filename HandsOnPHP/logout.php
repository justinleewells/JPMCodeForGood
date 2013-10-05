<?php
  include("database.php");
  
  $field = $_GET['identity'];
  $passw = $_GET['password'];
  
  if(strpos($field,"@") > 0) { 
    $column = "email";
  } else {
    $column = "username";
  }
  
  $q = mysql_query("UPDATE user SET logged_in = 0 WHERE $column='$field' AND password = $passw;") or die(mysql_error());
  $result = array("logged_in"=>"0");
  echo json_encode($result);
?>