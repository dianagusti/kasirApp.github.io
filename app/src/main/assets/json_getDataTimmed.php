<?php
if ($_GET["email"]) {
    require("init.php");
    
    $email = $_GET["email"];
    $sql = "select * from tenagamedis where user_email='$email';";
    $result = mysqli_query($con, $sql);
    $response = array();
    while ($row = mysqli_fetch_array($result)) {
    	array_push($response, array("Saran"=>$row["saran"]));
    }
    if (!empty($response)) {
        echo json_encode(array("server_timmedis"=>$response));
    }
    mysqli_close($con);
}
?>