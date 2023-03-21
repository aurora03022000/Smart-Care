<?php
if(isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID']);

    $sql = "UPDATE current_patient SET current_patient_id='$patientID' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){
        echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>