package esg;


public class Main {

	public static void main(String[] args) {
		
		double[][] measurements = {
				{0.04107, 0.04342, 0.04479, 0.04521, 0.04725, 0.04841, 0.04893, 0.04946, 0.05145, 0.0535, 0.05446, 0.05592, 0.05793},
				{0.04167, 0.04331, 0.0441, 0.04492, 0.04665, 0.04802, 0.04844, 0.04887, 0.05045, 0.05172, 0.05248, 0.0538, 0.05547},
				{0.0422, 0.0435, 0.04437, 0.0453, 0.04705, 0.04813, 0.04903, 0.04925, 0.05126, 0.0532, 0.05438, 0.05555, 0.05724},
				{0.04243, 0.04369, 0.04477, 0.0454, 0.04675, 0.04743, 0.04781, 0.04804, 0.04973, 0.05123, 0.05231, 0.05392, 0.05559},
				{0.04186, 0.04276, 0.04429, 0.04471, 0.04568, 0.04644, 0.04688, 0.04716, 0.04864, 0.05059, 0.05165, 0.05339, 0.05536},
				{0.04427, 0.04603, 0.04719, 0.04745, 0.0483, 0.04847, 0.04876, 0.0491, 0.05017, 0.05112, 0.05176, 0.05291, 0.05507},
				{0.04477, 0.04547, 0.04701, 0.04735, 0.04751, 0.04798, 0.04781, 0.048, 0.04907, 0.0497, 0.05035, 0.05081, 0.05163},
				{0.04498, 0.04607, 0.04641, 0.04656, 0.04721, 0.04719, 0.04739, 0.04731, 0.04806, 0.04881, 0.04924, 0.04981, 0.05076},
				{0.04473, 0.04537, 0.04542, 0.04539, 0.04573, 0.04552, 0.04581, 0.04543, 0.04588, 0.04673, 0.04717, 0.04815, 0.04967},
				{0.04467, 0.04567, 0.04589, 0.04598, 0.04642, 0.04671, 0.047, 0.04672, 0.04758, 0.04832, 0.04874, 0.04947, 0.05041},
				{0.04538, 0.04686, 0.04731, 0.04745, 0.04789, 0.04777, 0.04807, 0.04779, 0.04844, 0.04896, 0.04941, 0.05026, 0.05137},
				{0.04704, 0.04825, 0.04849, 0.04881, 0.04906, 0.04903, 0.04918, 0.04905, 0.04949, 0.04969, 0.05003, 0.05078, 0.05161},
				{0.04839, 0.0495, 0.04927, 0.04959, 0.04993, 0.04981, 0.04991, 0.04983, 0.04985, 0.04995, 0.05018, 0.05055, 0.05125},
				{0.04853, 0.049, 0.04857, 0.04861, 0.04865, 0.04854, 0.04873, 0.04866, 0.04868, 0.04889, 0.04911, 0.04988, 0.05085},
				{0.04894, 0.04831, 0.04849, 0.04802, 0.04812, 0.04791, 0.04781, 0.04782, 0.04835, 0.04868, 0.04889, 0.05002, 0.05142},
				{0.04986, 0.04997, 0.05037, 0.05027, 0.05047, 0.05057, 0.05056, 0.05056, 0.05067, 0.05078, 0.05113, 0.05175, 0.05243},
				{0.0489, 0.04876, 0.05005, 0.05038, 0.05093, 0.05127, 0.05124, 0.05136, 0.05188, 0.05231, 0.05264, 0.05298, 0.05354},
				{0.0487, 0.05007, 0.05127, 0.05135, 0.05205, 0.05234, 0.05254, 0.05275, 0.05367, 0.0541, 0.05466, 0.05512, 0.05567},
				{0.04949, 0.05085, 0.05181, 0.05184, 0.05203, 0.05203, 0.05203, 0.05213, 0.05255, 0.05277, 0.05323, 0.05357, 0.05397},
				{0.05135, 0.05295, 0.05283, 0.0529, 0.05396, 0.05389, 0.05409, 0.05409, 0.0544, 0.05461, 0.05507, 0.05556, 0.05595},
				{0.05119, 0.05212, 0.05283, 0.0532, 0.0541, 0.05429, 0.05449, 0.05459, 0.0551, 0.05532, 0.05553, 0.05603, 0.0566},
				{0.05147, 0.05192, 0.05234, 0.05282, 0.05386, 0.05411, 0.05429, 0.05419, 0.0546, 0.05493, 0.05514, 0.0555, 0.05591},
				{0.05183, 0.05299, 0.05362, 0.05477, 0.05756, 0.05866, 0.05843, 0.05831, 0.05806, 0.05738, 0.05644, 0.05692, 0.05707},
				{0.05246, 0.05445, 0.05519, 0.05672, 0.05951, 0.05888, 0.05835, 0.05814, 0.05782, 0.05714, 0.05609, 0.05606, 0.05612},
				{0.05009, 0.04978, 0.04988, 0.04948, 0.04938, 0.04999, 0.05091, 0.05121, 0.05161, 0.05236, 0.05207, 0.05232, 0.05273},
				{0.0489, 0.0487, 0.0488, 0.0487, 0.0488, 0.04911, 0.04962, 0.04992, 0.05128, 0.05169, 0.05212, 0.05231, 0.05257},
				{0.05028, 0.05007, 0.05037, 0.05056, 0.05086, 0.05106, 0.05116, 0.05126, 0.05168, 0.05211, 0.05244, 0.05252, 0.05279},
				{0.0488, 0.04851, 0.04861, 0.04861, 0.04861, 0.04901, 0.04921, 0.04942, 0.05036, 0.05077, 0.05121, 0.05128, 0.05154},
				{0.0493, 0.04919, 0.04939, 0.05008, 0.05168, 0.0537, 0.0547, 0.0552, 0.05609, 0.05627, 0.05657, 0.05652, 0.05663},
				{0.05028, 0.05104, 0.05185, 0.05243, 0.05402, 0.05706, 0.05909, 0.05926, 0.0598, 0.05974, 0.05994, 0.0599, 0.06002},
				{0.05275, 0.05309, 0.05371, 0.05398, 0.05648, 0.05849, 0.05856, 0.05854, 0.05861, 0.05893, 0.05901, 0.05898, 0.05897},
				{0.05246, 0.0527, 0.05351, 0.05408, 0.05668, 0.05798, 0.05816, 0.05825, 0.05896, 0.05949, 0.05956, 0.05964, 0.0596},
				{0.05354, 0.05445, 0.05518, 0.05554, 0.05693, 0.05743, 0.05762, 0.05782, 0.05801, 0.05821, 0.05832, 0.05869, 0.05879},
				{0.04801, 0.04792, 0.04861, 0.04861, 0.04881, 0.04901, 0.04962, 0.05024, 0.0529, 0.05569, 0.05688, 0.05704, 0.05691},
				{0.04208, 0.04489, 0.04718, 0.04806, 0.04985, 0.05155, 0.05338, 0.05398, 0.05506, 0.05762, 0.0595, 0.06033, 0.06027},
				{0.02731, 0.02948, 0.03092, 0.03226, 0.03426, 0.03586, 0.03799, 0.03921, 0.04123, 0.04337, 0.04476, 0.04892, 0.04888},
				{0.02035, 0.02119, 0.0228, 0.02447, 0.02897, 0.0329, 0.03676, 0.03972, 0.04326, 0.04669, 0.05051, 0.05459, 0.0544},
				{0.01736, 0.0196, 0.02232, 0.02518, 0.03068, 0.03531, 0.03855, 0.04256, 0.04669, 0.04921, 0.05332, 0.05566, 0.05556},
				{0.01906, 0.0205, 0.024, 0.02666, 0.03196, 0.03649, 0.04025, 0.04363, 0.04843, 0.05083, 0.05307, 0.05523, 0.05459},
				{0.01955, 0.02099, 0.0235, 0.02517, 0.03027, 0.03348, 0.03683, 0.03907, 0.04282, 0.04582, 0.04865, 0.05278, 0.05247},
				{0.01975, 0.02139, 0.023, 0.02407, 0.03008, 0.03431, 0.03816, 0.04123, 0.04825, 0.0505, 0.05347, 0.05667, 0.05622},
				{0.01955, 0.02218, 0.02539, 0.02944, 0.03634, 0.03955, 0.04186, 0.04379, 0.04788, 0.05145, 0.05335, 0.05648, 0.05622},
				{0.02124, 0.02168, 0.02459, 0.02815, 0.03455, 0.03959, 0.04252, 0.04485, 0.04946, 0.05267, 0.05528, 0.05743, 0.05752},
				{0.02293, 0.02544, 0.02965, 0.03388, 0.03888, 0.043, 0.04449, 0.0459, 0.05067, 0.05367, 0.05645, 0.05712, 0.05748},
				{0.02482, 0.02741, 0.03203, 0.03556, 0.04125, 0.04374, 0.04473, 0.04594, 0.05008, 0.0531, 0.05474, 0.0566, 0.05694},
				{0.02204, 0.02633, 0.03115, 0.03527, 0.04166, 0.04415, 0.04596, 0.04716, 0.0515, 0.05395, 0.05576, 0.05707, 0.05744},
				{0.02194, 0.02623, 0.02995, 0.03229, 0.03668, 0.03898, 0.04131, 0.04345, 0.04853, 0.0523, 0.0543, 0.0562, 0.0565},
				{0.02413, 0.03017, 0.03312, 0.03573, 0.04022, 0.04395, 0.04555, 0.04675, 0.05141, 0.05397, 0.05543, 0.05756, 0.05789},
				{0.02423, 0.0281, 0.03034, 0.03317, 0.03786, 0.04098, 0.04381, 0.04543, 0.04998, 0.05309, 0.0551, 0.05622, 0.05627},
				{0.02244, 0.02534, 0.02846, 0.0315, 0.0366, 0.0395, 0.04183, 0.04355, 0.04788, 0.05098, 0.05314, 0.05519, 0.05501},
				{0.02174, 0.02267, 0.02459, 0.02635, 0.03145, 0.03507, 0.03811, 0.04118, 0.04675, 0.04868, 0.05133, 0.05335, 0.05347},
				{0.02124, 0.02257, 0.02459, 0.02695, 0.03215, 0.03475, 0.03646, 0.0384, 0.04429, 0.04759, 0.04996, 0.05262, 0.05239},
				{0.02204, 0.02435, 0.02687, 0.02863, 0.03343, 0.03593, 0.03764, 0.03926, 0.04519, 0.04849, 0.0511, 0.05429, 0.05402},
				{0.02284, 0.02682, 0.02915, 0.03169, 0.03618, 0.03828, 0.03999, 0.04173, 0.04596, 0.04929, 0.0512, 0.05326, 0.05326},
				{0.02453, 0.02731, 0.03014, 0.03168, 0.03458, 0.03688, 0.03911, 0.04105, 0.0456, 0.04858, 0.05002, 0.05195, 0.05198},
				{0.02621, 0.02869, 0.03083, 0.03177, 0.03337, 0.03487, 0.03639, 0.03781, 0.04131, 0.04375, 0.04522, 0.04742, 0.04748},
				{0.02482, 0.02623, 0.02785, 0.02891, 0.03121, 0.0323, 0.03351, 0.03494, 0.03854, 0.04109, 0.04257, 0.04564, 0.04565},
				{0.02413, 0.02534, 0.02706, 0.02842, 0.03092, 0.03242, 0.03393, 0.03525, 0.04043, 0.04285, 0.04523, 0.0475, 0.04752},
				{0.02542, 0.02662, 0.02785, 0.02871, 0.03041, 0.03211, 0.03353, 0.03505, 0.04034, 0.0432, 0.04546, 0.04759, 0.0476},
				{0.02612, 0.02731, 0.02884, 0.03009, 0.03249, 0.0347, 0.03631, 0.03774, 0.04208, 0.04463, 0.04666, 0.04831, 0.04837},
				{0.02959, 0.03116, 0.03299, 0.03463, 0.03712, 0.03933, 0.04054, 0.04176, 0.04505, 0.04695, 0.04856, 0.04964, 0.04989},
				{0.03098, 0.03214, 0.03358, 0.03462, 0.03632, 0.03782, 0.03903, 0.04025, 0.04398, 0.04632, 0.04803, 0.04947, 0.04969},
				{0.03098, 0.03234, 0.03328, 0.03412, 0.03572, 0.03733, 0.03854, 0.03966, 0.04233, 0.04435, 0.04597, 0.04742, 0.04766},
				{0.03217, 0.03352, 0.03457, 0.0356, 0.0367, 0.0378, 0.03871, 0.03952, 0.04221, 0.0439, 0.04601, 0.04758, 0.0478},
				{0.03197, 0.03273, 0.03348, 0.03432, 0.03541, 0.03642, 0.03722, 0.03783, 0.03989, 0.04159, 0.04358, 0.04528, 0.04551},
				{0.03386, 0.0344, 0.03525, 0.03579, 0.03689, 0.03789, 0.03849, 0.0391, 0.04137, 0.04263, 0.04394, 0.04518, 0.04546},
				{0.03445, 0.03558, 0.03653, 0.03697, 0.03787, 0.03847, 0.03897, 0.03937, 0.04092, 0.04187, 0.04273, 0.04338, 0.04371},
				{0.03336, 0.03381, 0.03416, 0.03441, 0.03491, 0.03531, 0.03561, 0.03581, 0.03695, 0.03812, 0.03932, 0.04007, 0.04038},
				{0.03396, 0.0344, 0.03495, 0.0353, 0.03589, 0.03609, 0.03619, 0.03629, 0.03764, 0.03979, 0.04038, 0.04152, 0.04182},
				{0.03435, 0.03489, 0.03525, 0.03529, 0.03549, 0.03559, 0.03569, 0.036, 0.03714, 0.03831, 0.03951, 0.04149, 0.04175},
				{0.03346, 0.03362, 0.03386, 0.03401, 0.03421, 0.03431, 0.03441, 0.03462, 0.03576, 0.03726, 0.03857, 0.04078, 0.04102},
				{0.03415, 0.03401, 0.03406, 0.03411, 0.03411, 0.03411, 0.03421, 0.03431, 0.03545, 0.03685, 0.03872, 0.04105, 0.04127},
				{0.03336, 0.03332, 0.03357, 0.03372, 0.03402, 0.03422, 0.03432, 0.03442, 0.03556, 0.03684, 0.03861, 0.04007, 0.04033},
				{0.03386, 0.03391, 0.03406, 0.03421, 0.03431, 0.03461, 0.03481, 0.03501, 0.03605, 0.03733, 0.03887, 0.03998, 0.04013},
				{0.03425, 0.03421, 0.03445, 0.0345, 0.0348, 0.0352, 0.03561, 0.03602, 0.03746, 0.03885, 0.04027, 0.04089, 0.04106},
				{0.03356, 0.03381, 0.03406, 0.03411, 0.03431, 0.03441, 0.03461, 0.03482, 0.03595, 0.03734, 0.03877, 0.03975, 0.03991},
				{0.03306, 0.03303, 0.03317, 0.03322, 0.03332, 0.03332, 0.03342, 0.03353, 0.03467, 0.03595, 0.03748, 0.0387, 0.03886},
				{0.03257, 0.03253, 0.03268, 0.03273, 0.03293, 0.03313, 0.03333, 0.03354, 0.03447, 0.03542, 0.03696, 0.03807, 0.03823},
				{0.0278, 0.0279, 0.02804, 0.0282, 0.0285, 0.0287, 0.0288, 0.029, 0.03003, 0.03087, 0.03217, 0.0328, 0.03299},
				{0.0281, 0.028, 0.02814, 0.0281, 0.0281, 0.0281, 0.0281, 0.0281, 0.02873, 0.02957, 0.03087, 0.03138, 0.03158},
				{0.0281, 0.0281, 0.02814, 0.0282, 0.0283, 0.0284, 0.0284, 0.0285, 0.02902, 0.02976, 0.03062, 0.03127, 0.03147},
				{0.02701, 0.02721, 0.02745, 0.02761, 0.02771, 0.02791, 0.02811, 0.02832, 0.02872, 0.02935, 0.03011, 0.03087, 0.03108},
				{0.02721, 0.02751, 0.02764, 0.02781, 0.02801, 0.02831, 0.02851, 0.02871, 0.02922, 0.02985, 0.03083, 0.03182, 0.03213},
				{0.02731, 0.02751, 0.02774, 0.02781, 0.02811, 0.02831, 0.02851, 0.02871, 0.02984, 0.031, 0.03207, 0.03352, 0.03381},
				{0.02711, 0.02721, 0.02735, 0.02741, 0.02751, 0.02761, 0.02781, 0.02792, 0.02894, 0.02989, 0.0314, 0.03273, 0.03302},
				{0.02621, 0.02623, 0.02636, 0.02633, 0.02643, 0.02643, 0.02653, 0.02673, 0.02745, 0.02861, 0.03002, 0.03158, 0.03185},
				{0.02542, 0.02534, 0.02537, 0.02534, 0.02554, 0.02564, 0.02574, 0.02574, 0.02594, 0.02722, 0.02839, 0.03112, 0.03137},
				{0.02602, 0.02583, 0.02586, 0.02553, 0.02554, 0.02533, 0.02534, 0.02544, 0.02575, 0.0267, 0.0282, 0.02987, 0.03066},
				{0.02572, 0.02583, 0.02616, 0.02633, 0.02673, 0.02753, 0.02793, 0.02824, 0.02916, 0.0301, 0.03171, 0.03292, 0.03399},
				{0.02582, 0.02603, 0.02656, 0.02692, 0.02762, 0.02853, 0.02893, 0.02954, 0.03159, 0.03316, 0.03454, 0.03587, 0.03694},
				{0.02602, 0.02613, 0.02646, 0.02663, 0.02743, 0.02833, 0.02914, 0.02984, 0.03221, 0.034, 0.03548, 0.03691, 0.03798},
				{0.02552, 0.02564, 0.02636, 0.02673, 0.02733, 0.02763, 0.02813, 0.02925, 0.03183, 0.03417, 0.03598, 0.03763, 0.03868},
				{0.02562, 0.02603, 0.02656, 0.02683, 0.02722, 0.02783, 0.02813, 0.02874, 0.03079, 0.03258, 0.03475, 0.03653, 0.03772},
				{0.02552, 0.02583, 0.02626, 0.02663, 0.02698, 0.02743, 0.02808, 0.02849, 0.03049, 0.03228, 0.03424, 0.03639, 0.03756},
				{0.02547, 0.02578, 0.02631, 0.02673, 0.02743, 0.02848, 0.02972, 0.03066, 0.03379, 0.03529, 0.03736, 0.0394, 0.04059},
				{0.02577, 0.02603, 0.02641, 0.02673, 0.02732, 0.02788, 0.02843, 0.02924, 0.03266, 0.03443, 0.03651, 0.03807, 0.0392},
				{0.02582, 0.02613, 0.02651, 0.02668, 0.02727, 0.02798, 0.02858, 0.02945, 0.03255, 0.0346, 0.03686, 0.03873, 0.03999},
				{0.02572, 0.02608, 0.02641, 0.02653, 0.02691, 0.02758, 0.0281, 0.02898, 0.03139, 0.03317, 0.03569, 0.03748, 0.03874},
				{0.02582, 0.02608, 0.02631, 0.02668, 0.02707, 0.02773, 0.02833, 0.02915, 0.03181, 0.03398, 0.03586, 0.03759, 0.03886},
				{0.02592, 0.02618, 0.02641, 0.02668, 0.02712, 0.02778, 0.02833, 0.02902, 0.03193, 0.03409, 0.03596, 0.03751, 0.03885},
				{0.02582, 0.02608, 0.02646, 0.02663, 0.02708, 0.02758, 0.02806, 0.02854, 0.03075, 0.03232, 0.0341, 0.03519, 0.03647},
				{0.02572, 0.02583, 0.02606, 0.02608, 0.0263, 0.02645, 0.02686, 0.02724, 0.02922, 0.03067, 0.03237, 0.03364, 0.03491},
				{0.02413, 0.02406, 0.02413, 0.0241, 0.02442, 0.02464, 0.02506, 0.02552, 0.0279, 0.02956, 0.03102, 0.03243, 0.03365},
				{0.02308, 0.02326, 0.02354, 0.02361, 0.0241, 0.02451, 0.02494, 0.02554, 0.02778, 0.02957, 0.03099, 0.03232, 0.03354},
				{0.02199, 0.02203, 0.02215, 0.02223, 0.02242, 0.0225, 0.02282, 0.02338, 0.02523, 0.02739, 0.02927, 0.03054, 0.03175},
				{0.0204, 0.02064, 0.02091, 0.02099, 0.02124, 0.0213, 0.02153, 0.02192, 0.02341, 0.02548, 0.02745, 0.02878, 0.02996},
				{0.02035, 0.0204, 0.02052, 0.02059, 0.02069, 0.02075, 0.02091, 0.02125, 0.02277, 0.02459, 0.02682, 0.0283, 0.02946},
				{0.02075, 0.02079, 0.02097, 0.02106, 0.02112, 0.02119, 0.02127, 0.02155, 0.02332, 0.02483, 0.02693, 0.0284, 0.02953},
				{0.02, 0.01995, 0.02, 0.02006, 0.02011, 0.02013, 0.02014, 0.02024, 0.02094, 0.02199, 0.02317, 0.02422, 0.02535},
				{0.02025, 0.02025, 0.02032, 0.02037, 0.02059, 0.02069, 0.02077, 0.02088, 0.02154, 0.02285, 0.02435, 0.02573, 0.02686},
				{0.01719, 0.01717, 0.01719, 0.0172, 0.01709, 0.0171, 0.01723, 0.01763, 0.01848, 0.02011, 0.02244, 0.02399, 0.02509},
				{0.01719, 0.01727, 0.01733, 0.01746, 0.01753, 0.01791, 0.01817, 0.01873, 0.02083, 0.02256, 0.02512, 0.02692, 0.02802},
				{0.01687, 0.01685, 0.0169, 0.01697, 0.01698, 0.01709, 0.01725, 0.01765, 0.01947, 0.02156, 0.02394, 0.02562, 0.02671},
				{0.01563, 0.01576, 0.01601, 0.01618, 0.01664, 0.01717, 0.01772, 0.01821, 0.02104, 0.02329, 0.02542, 0.02728, 0.02824},
				{0.01508, 0.01521, 0.01561, 0.01579, 0.01632, 0.01685, 0.01741, 0.01799, 0.02055, 0.02291, 0.02527, 0.0269, 0.02787},
				{0.01533, 0.01546, 0.01581, 0.01599, 0.01633, 0.01671, 0.01713, 0.01768, 0.01946, 0.02171, 0.02396, 0.02531, 0.02629},
				{0.01503, 0.01503, 0.01518, 0.01521, 0.0154, 0.01546, 0.01571, 0.01598, 0.01737, 0.01913, 0.02164, 0.02277, 0.02377},
				{0.0151, 0.01523, 0.01551, 0.01576, 0.01618, 0.01633, 0.01658, 0.01684, 0.0181, 0.01977, 0.02209, 0.02292, 0.02361},
				{0.01645, 0.01662, 0.017, 0.01717, 0.01752, 0.01771, 0.01786, 0.01816, 0.01976, 0.02133, 0.02344, 0.02436, 0.02514},
				{0.01545, 0.01586, 0.01607, 0.01631, 0.01634, 0.01659, 0.01672, 0.01691, 0.01837, 0.02032, 0.02168, 0.02236, 0.02295},
				{0.01499, 0.01508, 0.01525, 0.01539, 0.01551, 0.0157, 0.0158, 0.01591, 0.01721, 0.01911, 0.02068, 0.02139, 0.02201},
				{0.0146, 0.01461, 0.01465, 0.01469, 0.01472, 0.01475, 0.01484, 0.01494, 0.01579, 0.01739, 0.01871, 0.0193, 0.01984},
				{0.01485, 0.01484, 0.01485, 0.01485, 0.01487, 0.01494, 0.01496, 0.01496, 0.01575, 0.01722, 0.01879, 0.01952, 0.02003},
				{0.01478, 0.01477, 0.01478, 0.0148, 0.01482, 0.01496, 0.01507, 0.01508, 0.01574, 0.01698, 0.01814, 0.01887, 0.01921},
				{0.01468, 0.01467, 0.01493, 0.0151, 0.01523, 0.01536, 0.01545, 0.01556, 0.01605, 0.01715, 0.01828, 0.01899, 0.01927},
				{0.01241, 0.01263, 0.01272, 0.01278, 0.0129, 0.013, 0.01307, 0.01309, 0.01324, 0.01396, 0.01478, 0.01551, 0.0158},
				{0.01214, 0.0122, 0.01221, 0.01231, 0.01242, 0.0125, 0.01255, 0.0126, 0.01274, 0.01308, 0.01394, 0.01457, 0.01479},
				{0.01251, 0.01258, 0.01281, 0.0129, 0.01315, 0.01338, 0.01348, 0.0136, 0.01393, 0.01427, 0.01497, 0.01534, 0.01542},
				{0.01263, 0.01281, 0.01308, 0.01319, 0.01321, 0.01323, 0.01326, 0.01328, 0.01341, 0.01362, 0.01433, 0.01463, 0.01474},
				{0.0133, 0.01352, 0.01395, 0.01408, 0.01438, 0.01482, 0.0149, 0.0151, 0.01568, 0.01605, 0.01692, 0.01739, 0.0177},
				{0.01519, 0.01571, 0.01623, 0.01646, 0.0168, 0.01721, 0.01728, 0.01763, 0.01959, 0.0207, 0.02166, 0.0217, 0.02174},
				{0.0134, 0.01447, 0.01536, 0.01568, 0.01629, 0.01637, 0.01657, 0.01659, 0.01819, 0.02011, 0.02112, 0.02167, 0.02169}
		};
		double[] initialState = new double[] {0.0258,  -0.0104, 0.0032};
		double[][] initialError = new double[][] {{1., 0., 0.}, {0., 1., 0.}, {0., 0., 1.}};
		double[] initialParam = new double[] {0.60731, 0.001, 0.12844, 0.34156, 0.70498, 0.04029, -0.02018, -0.00900, 0.00653, -0.00610, 0.00420, 0.00009, -0.00468, 0.01036};
				
		MultivariateFunction fn = x -> {
			DynamicNelsonSiegel dns = new DynamicNelsonSiegel(x.getData());
			dns.setState(initialState, initialError);
			double logL = dns.estimate(measurements);
			return logL;
		};
		
		NelderMead nm = new NelderMead();
		Vector param = nm.optimize(fn, new Vector(initialParam));
		(new Vector(initialParam)).print();
		param.print();
		
		
		
	}
}
