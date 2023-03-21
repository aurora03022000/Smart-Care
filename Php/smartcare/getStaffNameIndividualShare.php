<?php
if(isset($_POST['currentUserID']) && isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);
    $patientID = validate($_POST['patientID']);

    $sql = "SELECT * FROM patient WHERE patient_id ='$patientID' and isExist=1";
    $result = mysqli_query($conn, $sql);
    
    if($result){
       while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM user WHERE user_id !='$currentUserID' and user_id != '$row[share_id]' and isExist=1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row1=mysqli_fetch_assoc($result1)){
                    echo $row1['staff_name']."`";
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