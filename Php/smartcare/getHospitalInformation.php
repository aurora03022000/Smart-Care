<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM hospital_information";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['hospital_name']."`".$row['hospital_address']."`".$row['hospital_email']."`".$row['hospital_date_acquired'];
        }
    }

    else{
        echo 'failure';
    }
?>