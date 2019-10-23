<?php
	
	//Import File Koneksi database
	require_once('conn.php');

	if($_SERVER['REQUEST_METHOD']=='POST'){

		//Mendapatkan Nilai Variable
		$nama = $_POST['nama'];
		$email = $_POST['email'];
		$password = $_POST['password'];		
		$date = date("Y-m-d H:i:s");
		
		//$confirm_password = $_POST['confirm_password'];

		// get from database
		$query = "SELECT email, password from users";
		$result = mysqli_query($con, $query);
		
		if(!$result){
			echo 'Silahkan check database anda';
		}
		
		$row = mysqli_fetch_assoc($result);

		if($email == $row['email']){
			echo 'Gagal, alamat email sudah terdaftar';
			//$_SESSION['admin'] = false;
		} else {
			
			//Pembuatan Syntax SQL
			$sql = "INSERT INTO users (nama,email,password,user_created,user_updated) VALUES ('$nama','$email','$password','$date','$date')";

			//Eksekusi Query database
			if(mysqli_query($con, $sql)){
				
				echo 'Berhasil Menambahkan User';
			}else{
				echo 'Gagal Menambahkan User';
			}
		}
		
		mysqli_close($con);
	}

?>	