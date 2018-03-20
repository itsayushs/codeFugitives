#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 20 21:05:36 2018

@author: stark
"""

import os
def patch_pi(st_id, band_id,f_id,parm):
    combine_str=''+st_id+'-abc-'+band_id+'-abc-'+f_id+'-abc-'+parm
    command="sshpass -p a ssh pi@172.26.69.4 echo '{}' >  /home/pi/.capture.txt".format(combine_str) 
    os.system(command)
    