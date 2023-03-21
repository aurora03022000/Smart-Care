<?php
    require_once "conn.php";
    require_once "validate.php";

    $sql = "SELECT * FROM current_patient_monitored";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){

            echo $row['patient_id'];
        }
    }

    else{
        echo 'failure';
    }

?>