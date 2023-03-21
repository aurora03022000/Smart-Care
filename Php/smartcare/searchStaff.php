<?php
if(isset($_POST['staffName']) && isset($_POST['currentUser'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $staffName = validate($_POST['staffName'] );
    $currentUser = validate($_POST['currentUser'] );

    $sql = "SELECT * FROM user WHERE user_id != '$currentUser' and staff_name='$staffName' and username != 'admin' and isExist=1 ORDER BY staff_name ASC";
    $result = $conn->query($sql);
    $rowsNumber = mysqli_num_rows($result);

    if($rowsNumber > 0){

            while($row = mysqli_fetch_assoc($result)){
                echo $row['staff_name']."`".$row['user_id']."%";
            }

    }

    else if($rowsNumber == 0){
        if($staffName == ""){
            $sql1 = "SELECT * FROM user WHERE user_id != '$currentUser' and isExist=1 and username != 'admin' ORDER BY staff_name ASC";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row1 = mysqli_fetch_assoc($result1)){
                    echo $row1['staff_name']."`".$row1['user_id']."%";
                }
            }
        }

        else{
            echo "empty";
        }

    }

    else{
        echo 'failure';
    }

}
?>