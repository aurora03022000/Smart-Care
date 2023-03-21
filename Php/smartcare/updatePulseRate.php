<?php
if(isset($_POST['pulseRateValue']) && isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $pulseRateValue = validate($_POST['pulseRateValue']);
    $patientID = validate($_POST['patientID']);

    $sql = "UPDATE patient_health_log SET pulse_rate='$pulseRateValue' WHERE patient_id='$patientID' and isExist=1";
    $result = mysqli_query($conn, $sql);

    if($result){
        echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>