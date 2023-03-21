<?php
if(isset($_POST['patient_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patient_id = validate($_POST['patient_id']);
    
    $sql = "UPDATE current_ids_admin SET current_patient_clicked_id = '$patient_id'";
    $result = $conn->query($sql);

        if($result){
            echo 'success';
        }

        else{
            echo 'failure';
        }
}
?>