class tygrlib:
    def __init__(self):
        from ctypes import cdll
        self.lib = cdll.LoadLibrary('./libpcm2wav.so')

    def pcm2wav(self,src_filename,dst_filename):
        res = self.lib.pcm2wav(src_filename,dst_filename)


test_filename = "test10.pcm"
output_filename = "test10.wav"


lib = tygrlib()
lib.pcm2wav(test_filename,output_filename)


