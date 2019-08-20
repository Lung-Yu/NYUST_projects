import numpy as np

def split_frame(rate,data,split_len=512,overlap=0.25):
    frames = []
    
    for i in range(0,len(data)):
        start_idx = i * int((split_len * overlap))
        end_idx = start_idx + split_len
        if end_idx >= len(data):
            break
        
        frame = data[start_idx:end_idx]
        frames.append(np.array(frame))

    return frames

rate,data = wavfile.read(file_path)
frames = split_frame(rate,data)