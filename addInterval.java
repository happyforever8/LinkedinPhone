实现一个datastructure class，里面有两个function，一个是 void addIntervals(int from, int to)，另一个是int getCoverageLength();
比如:
addIntervals(3,7)
addIntervals(8,10)
addIntervals(1,5)
getCoverageLength() -> 6 + 2 = 8

class Interval {
	int from;
	int to;

	public Interval(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public int getDiff() {
		return to - from;
	}
}

class IntervalCalculator {
	List<Interval> intervals;

	public IntervalCalculator() {
		this.intervals = new ArrayList<>();
	}

	public void addInterval(int from, int to) {
		this.intervals.add(new Interval(from, to));
	}

	public int getTotalCoveredLength() {
		if (this.intervals.size() == 0)
			return 0;

		List<Interval> merged = getMergedIntervals();

		int mergedLength = 0;
		for (Interval interval : merged) {
			mergedLength += interval.getDiff();
		}

		return mergedLength;
	}

	private List<Interval> getMergedIntervals() {
		sortIntervals();

		List<Interval> merged = new ArrayList<>();
		merged.add(this.intervals.get(0));
		
		for (int i = 1; i < this.intervals.size(); i++) {
			Interval lastMerged = merged.get(merged.size() - 1);
			Interval current = intervals.get(i);

			if (lastMerged.to < current.from) {
				merged.add(current);
			} else if (lastMerged.to >= current.from) {
				merged.remove(merged.size() - 1);
				merged.add(new Interval(lastMerged.from, current.to));
			}
		}

		return merged;
	}

	private void sortIntervals() {
		Collections.sort(this.intervals, new Comparator<Interval>() {
			@Override
			public int compare(Interval a, Interval b) {
				return Integer.compare(a.from, b.from);
			}
		});
	}
	
}






class RangeModule {
    SegmentNode root;
    int max = (int) Math.pow(10,9);
    public RangeModule() {
        root = new SegmentNode(0,max,false);
    }

    public void addRange(int left, int right) {
        update(root,left,right,true);
    }
    private boolean update(SegmentNode node,int l,int r,boolean state){
        if(l<=node.l && r>=node.r){
            node.state = state;
            node.left = null;
            node.right = null;
            return node.state;
        }
        if(l>=node.r || r<=node.l) return node.state;
        int mid = node.l + (node.r - node.l) / 2;
        if(node.left==null){
            node.left = new SegmentNode(node.l,mid,node.state);
            node.right = new SegmentNode(mid,node.r,node.state);
        }
        boolean left = update(node.left, l, r,state);
        boolean right = update(node.right, l, r,state);
        node.state = left && right;
        return node.state;
    }

    public boolean queryRange(int left, int right) {
        return query(root,left,right);
    }
    private boolean query(SegmentNode node,int l,int r){
        if(l>=node.r || r<=node.l) return true;
        if((l<=node.l && r>=node.r) || (node.left==null)) return node.state;
        int mid = node.l + (node.r - node.l) / 2;
        if(r<=mid){
            return query(node.left,l,r);
        }else if(l>=mid){
            return query(node.right,l,r);
        }else{
            return query(node.left,l,r) && query(node.right,l,r);
        }
    }

    public void removeRange(int left, int right) {
        update(root,left,right,false);
    }
}
class SegmentNode{
    public int l, r;
    public boolean state;
    public SegmentNode left, right;
    public SegmentNode(int l,int r,boolean state){
        this.l = l;
        this.r = r;
        this.state = state;
    }
}

