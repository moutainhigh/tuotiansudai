#!/bin/bash

clear_cache(){
    find . -type f -name "*.py[co]" -delete
    find . -type d -name "__pycache__" -delete
}

install(){
    echo "install..."
    source /root/.current/bin/activate
    pip install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirements.txt
    echo "install done"
}

exit_code=0

ut(){
    cp /workspace/deploy-config/ttsd-current/settings_local.py .
    exit_code = python manage.py test --noinput
    deactivate
}

clear_cache && install && ut

exit exit_code