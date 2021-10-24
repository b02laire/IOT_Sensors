full_path=$(realpath $0)
dir_path=$(dirname $full_path)
cd src
java --module-path $dir_path/javafx-sdk-17.0.0.1/lib --add-modules javafx.controls App

