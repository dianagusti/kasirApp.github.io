<?php
	require("conn.php");
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$email = trim($_POST['email']);
		$password = trim($_POST['password']);

		$email = mysqli_real_escape_string($con, $email);
		$password = mysqli_real_escape_string($con, $password);

		// get from database
		$query = "SELECT * FROM users WHERE email='$email' AND password='$password'"; //email, password
		$result = mysqli_query($con, $query);
		
		if(!$result){
			echo 'Silahkan check database anda';
		}
		
		$row = mysqli_fetch_assoc($result);

		if(!empty($row)){
			echo 'Login user berhasil';
			//$_SESSION['admin'] = true;
		} else {
			echo 'Anda tidak memiliki otoritas masuk';
			//$_SESSION['admin'] = false;
		}
	}
	
	mysqli_close($con);
?>