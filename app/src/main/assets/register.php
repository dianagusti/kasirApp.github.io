<?php
require("init.php");

class usr{}
$nama = mysqli_real_escape_string($con, $_POST["nama"]);
$email = mysqli_real_escape_string($con, $_POST["email"]);
$password = mysqli_real_escape_string($con, $_POST["password"]);
$confirm_password = mysqli_real_escape_string($con, $_POST["confirm_password"]);

if ((empty($email))) { 
	$response = new usr();
	$response->success = 0;
	$response->message = "Email tidak boleh kosong !"; 
	die(json_encode($response));
} else if ((empty($password))) { 
	$response = new usr();
	$response->success = 0;
	$response->message = "Password tidak boleh kosong !"; 
	die(json_encode($response));
} else if ((empty($confirm_password)) || $password != $confirm_password) { 
	$response = new usr();
	$response->success = 0;
	$response->message = "Konfirmasi password tidak sama !"; 
	die(json_encode($response));
} else {
	if (!empty($nama) && !empty($email) && $password == $confirm_password){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM users WHERE email='".$email."'"));
	    if ($num_rows == 0){
    	    $query = mysqli_query($con, "INSERT INTO users (id, nama, email, password) VALUES(null,'".$nama."','".$email."','".md5($password)."')");
            if ($query){
	            $response = new usr();
	            $response->success = 1;
	            $response->message = "Registrasi berhasil, silahkan login !";
	            die(json_encode($response));
	        } else {
	            $response = new usr();
	            $response->success = 0;
	            $response->message = "Email sudah terdaftar !";
	            die(json_encode($response));
	 		}
	 	} else {
			$response = new usr();
     		$response->success = 0;
	 		$response->message = "Email sudah terdaftar !";
	 		die(json_encode($response));
	 	}
    }
}

mysqli_close($con);
?>	