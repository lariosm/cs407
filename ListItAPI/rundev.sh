#!/usr/bin/env bash
export FLASK_APP=app.py
export FLASK_ENV=development
set -a
. ./atlas.settings
set +a

flask run --host=localhost --port=$1