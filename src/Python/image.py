# For 중복 이미지 처리
photo_list = []

"""
S3 로 파일 불러오기.



"""
# pip install opencv-python
import cv2
from skimage.metrics import structural_similarity as ssim

import urllib.request
import numpy as np


# S3 Object URL -> Convert -> IMAGE
def url_to_image(URL):
    resp = urllib.request.urlopen(URL)
    image = np.asarray(bytearray(resp.read()), dtype="uint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)

    return image


image = url_to_image("https://khufcloud.s3.ap-northeast-2.amazonaws.com/test.png")
image2 = url_to_image("https://khufcloud.s3.ap-northeast-2.amazonaws.com/test2.png")

gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
gray2 = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY)

# grayscale 로 전환해서 Img_list 에 넣어두자.

(score, diff) = ssim(gray, gray2, full=True)

print(score)  # score == 1 -> 이미지 비교 동일하다.


# def dupImage(img_list):
#     for a in range(len(img_list)):
#         for b in range(a + 1, len(img_list)):
#             if img_list[a] == img_list[b] :


"""
generated_signed_url = create_presigned_url(you_bucket_name, bucket_key, 
seven_days_as_seconds, s3_signature['v4'])
print(generated_signed_url)
image_complete = url_to_image(generated_signed_url)

"""
