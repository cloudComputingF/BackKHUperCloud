# For 중복 이미지 처리
photo_list = []

"""
S3 로 파일 불러오기.



"""
# pip install opencv-python
import cv2
from skimage.metrics import structural_similarity as ssim

import urllib
import numpy as np


# S3 Object URL -> Convert -> IMAGE
def url_to_image(URL):
    resp = urllib.urlopen(URL)
    image = np.asarray(bytearray(resp.read()), dtype="unint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)

    return image
