<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM patient WHERE encoder ='$currentUserID' and share_id != '0' and isExist=1 ORDER BY share_id";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM user WHERE user_id ='$row[share_id]' AND user_id != '$currentUserID' AND isExist=1";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row1 = mysqli_fetch_assoc($result1)){
                    echo $row1['staff_name']."`".$row1['user_id']."`".$row['patient_id']."`".$row['patient_name']."`".$row['encoder'].'%';
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