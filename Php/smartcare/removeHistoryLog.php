<?php
if(isset($_POST['patient_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patient_id = validate($_POST['patient_id']);
    
        $sql1 = "UPDATE patient SET isExist=0 WHERE patient_id='$patient_id'";
        $result1 = $conn->query($sql1);

        if($result1){
            echo 'success';
        }

        else{
            echo 'failure';
        }
}
?>