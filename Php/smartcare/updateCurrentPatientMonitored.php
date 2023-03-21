<?php
if(isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID']);

    $sql = "UPDATE current_patient_monitored SET patient_id = '$patientID'";
    $result = $conn->query($sql);
    
    if($result){
       echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>