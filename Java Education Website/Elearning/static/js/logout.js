function log(){
    var confirmResult = confirm("Are you sure you want to logout?");
        if (confirmResult) {
            return true;
        }
        else{
            return false;
        }
}