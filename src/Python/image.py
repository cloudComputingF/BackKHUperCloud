import cv2  # pip install opencv-python
from skimage.metrics import structural_similarity as ssim
import urllib.request
import numpy as np
import sys
import json


# # S3 Object Key -> Convert -> IMAGE
def url_to_image(URL):
    resp = urllib.request.urlopen(URL)
    image = np.asarray(bytearray(resp.read()), dtype="uint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)

    return image


def main():
    url_list = json.loads(sys.argv[1])

    image_list = []
    # 사진 파일들을 image_list 에 추가.
    for i in range(len(url_list)):
        if (
            url_list[i]["download"].find(".PNG") != -1
            or url_list[i]["download"].find(".jpg") != -1
        ):
            image_list.append(url_list[i]["download"])

    # 데이터의 양이 많아지면 중복들을 제거하는게 좋을 듯 하다.
    for a in range(0, len(image_list)):
        image1 = url_to_image(image_list[a])
        image1g = cv2.cvtColor(image1, cv2.COLOR_BGR2GRAY)
        for b in range(0, len(image_list)):
            image2 = url_to_image(image_list[b])
            image2g = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY)
            (score, diff) = ssim(image1g, image2g, full=True)
            if (score) == 1:
                print(image_list[a], image_list[b], " : 이미지 중복")


if __name__ == "__main__":
    main()
