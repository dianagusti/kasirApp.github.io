<?php
require("init.php");
class usr{}
$email = mysqli_real_escape_string($con, $_POST["email"]);
$password = md5(mysqli_real_escape_string($con, $_POST["password"]));

if ((empty($email)) || (empty($password))) { 
	$response = new usr();
	$response->success = 0;
	$response->message = "Email dan password tidak boleh kosong !"; 
	die(json_encode($response));
}

$query = mysqli_query($con, "SELECT * FROM users WHERE email='$email' AND password='$password'");
$row = mysqli_fetch_array($query);
if (!empty($row)){
	$response = new usr();
	$response->success = 1;
	$response->message = "Selamat datang ".$row['nama']." di Herbal Twinning System";
	$response->id = $row['id'];
	$response->nama = $row['nama'];
	$response->email = $row['email'];
	die(json_encode($response));
} else { 
	$response = new usr();
	$response->success = 0;
	$response->message = "Email atau password salah !";
	die(json_encode($response));
}

mysqli_close();
?>