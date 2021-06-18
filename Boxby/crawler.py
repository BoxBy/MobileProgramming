import urllib
from bs4 import BeautifulSoup
import re
import os
import time
from bs4.builder import TreeBuilderRegistry
from tqdm import tqdm

def PageCwarler(recipeUrl):
    url = 'http://www.10000recipe.com' + str(recipeUrl)

    req = urllib.request.Request(url)
    sourcecode = urllib.request.urlopen(url).read()
    soup = BeautifulSoup(sourcecode, 'html.parser')

    recipe_title = ['[제목]']
    recipe_source = {}
    recipe_step = ['[단계]']
    recipe_image = []
    recipe_tag = ['[태그]']

    # Find Title
    try:
        res = soup.find('div', 'view2_summary')
        res = res.find('h3')
        recipe_title.append(res.get_text())
        res = soup.find('div', 'view2_summary_info')
        recipe_title.append(res.get_text().replace('\n', ''))

        res = soup.find('div', 'ready_ingre3')
    except:
        print('Title Error')
        return None

    # Find Title_Image
    try:
        img = soup.find('div', 'centeredcrop')
        img = img.find('img')
        recipe_image.append(('title', img['src']))
    except:
        print('Title Image Error')
        return None

    # Find Source
    try:
        for n in res.find_all('ul'):
            source = []
            title = n.find('b').get_text()
            recipe_source[title] = ''
            for tmp in n.find_all('li'):
                source.append(tmp.get_text().replace('\n', '').replace(' ', ''))
                recipe_source[title] = source
    except (AttributeError):
        print('Source Error')
        return None
    
    # Find Step & Step Image
    try:
        res = soup.find('div', 'view_step')
        i = 0
        for n in res.find_all('div', 'view_step_cont'):
            i = i + 1
            recipe_step.append('#' + str(i) + ' ' + n.get_text().replace('\n', ' '))
            n = n.find('img')
            if n is not None:
                recipe_image.append((i, n['src']))
    except:
        print('Step & Step Image Error')
        return None
    
    # Tag
    if (soup.find('div', 'view_tag')):
        recipe_tag = recipe_tag + soup.find('div', 'view_tag').get_text()[1:].split('#')

    if not recipe_step:
        return None

    recipe_step = [re.sub(r'@\d+.+', '', recipe) for recipe in recipe_step] # 다른 레시피로 리다이렉트되는 링크 텍스트 제거

    recipe = []
    for key, value in recipe_source.items():
        recipe.append(key)
        recipe = recipe + value

    recipe_all = recipe_title + recipe + recipe_step + recipe_tag

    return recipe_all, recipe_image

if __name__ == '__main__':
    file_dir = 'D:\OneDrive - konkuk.ac.kr\학교\모프\레시피'
    start = time.time()
    i = 1
    regex = re.compile(r'\d+')
    max = int(input("max recipe : "))
    recipe_count = 0
    while(True):
        print(f'{i} page Crawling...')
        url = f'https://www.10000recipe.com/recipe/list.html?&order=reco&page={i}'
        req = urllib.request.Request(url)
        sourcecode = urllib.request.urlopen(url).read()
        soup = BeautifulSoup(sourcecode, 'html.parser')
        for href in tqdm(soup.find('ul', 'common_sp_list_ul').find_all('li')):
            urls = href.find('a')['href']
            dir = regex.findall(urls)[0]
            recipe, images = PageCwarler(urls)
            recipe_dir = file_dir + f'\{dir}'
            if recipe is not None:
                if not os.path.isdir(file_dir + f'\{dir}'):
                    os.mkdir(recipe_dir)
                with open(recipe_dir + f'\{dir}.txt', 'w', encoding='utf-8') as outFile:
                    outFile.write('\n'.join(recipe))
                    recipe_count = recipe_count + 1
                for image in images:
                    with urllib.request.urlopen(image[1]) as f:
                        with open(recipe_dir + f'\{image[0]}.jpg', 'wb') as outImage:
                            img = f.read()
                            outImage.write(img)
                if recipe_count >= max:
                    break 
        print('Complete')
        if recipe_count >= max:
            break   
        i = i + 1     
    
    print(f'Total Time : {time.time() - start}')