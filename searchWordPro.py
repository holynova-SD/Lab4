# -*- coding: utf-8 -*-
"""
Created on Thu Sep 14 19:24:14 2017

@author: qiusuo
"""
import sys
from bs4 import BeautifulSoup
import requests

head="http://www.baidu.com/s?wd="
tail="&cl=3"

def get_pro(word):
    url=head+word+tail
    r=requests.get(url)
    r.raise_for_status()
    r.encoding=r.apparent_encoding
    soup=BeautifulSoup(r.text,"html.parser")
    line=str(soup.find_all("span",{'class':'op_dict_text2 op_dict3_else_items'}))
    if("原型" in line):
        print(line.split('>')[3].split('<')[0])
    else:
        print (word)
    
    
get_pro(sys.argv[1])