<?php
if(isset($_POST['patient_id'])){
    require_once "conn.php";
    require_once "validate.php";
        
    $patientID = validate($_POST['patient_id']);

    $checkSharer = "SELECT * FROM patient WHERE patient_id = '$patientID'";
    $checkSharerResult = mysqli_query($conn, $checkSharer);

    if($checkSharerResult){
        while($row1 = mysqli_fetch_assoc($checkSharerResult)){
            $getStaffName = "SELECT * FROM user WHERE user_id = '$row1[share_id]' and username != 'admin' ";
            $getStaffNameResult = mysqli_query($conn, $getStaffName);

            if($getStaffNameResult){
                while($row2 = mysqli_fetch_assoc($getStaffNameResult)){
                    echo $row1['share_id'].'`'.$row2['staff_name'];
                }
            }

            else{
                echo 'failure';
            }
        }
    }

    else{
        echo 'failure';
    }
}
?>