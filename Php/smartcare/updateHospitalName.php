<?php
if(isset($_POST['hospital_name']) && isset($_POST['admin_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $hospital_name = validate($_POST['hospital_name']);
    $admin_id = validate($_POST['admin_id']);

    $sql = "UPDATE hospital_information SET hospital_name = '$hospital_name' WHERE admin_id = '$admin_id' ";
    $result = $conn->query($sql);
    
    if($result){
        $sql1 = "UPDATE `admin` SET firstTime = 1 WHERE admin_id = '$admin_id' ";
        $result1 = $conn->query($sql1);

        if($result1){
            echo 'success';
        }

        else{
            echo 'failure';
        }

    }

    else{
        echo 'failure';
    }

}
?>